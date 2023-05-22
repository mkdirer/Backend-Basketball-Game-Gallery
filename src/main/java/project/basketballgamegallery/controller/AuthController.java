package project.basketballgamegallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import project.basketballgamegallery.model.EnumAccessRight;
import project.basketballgamegallery.model.AccessRight;
import project.basketballgamegallery.model.User;
import project.basketballgamegallery.payload.request.LoginRequest;
import project.basketballgamegallery.payload.request.SignupRequest;
import project.basketballgamegallery.payload.response.JwtResponse;
import project.basketballgamegallery.payload.response.MessageResponse;
import project.basketballgamegallery.repository.AccessRightRepository;
import project.basketballgamegallery.repository.UserRepository;
import project.basketballgamegallery.security.jwt.JwtUtils;
import project.basketballgamegallery.security.services.UserDetailsImpl;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Klasa odpowiedzialna za ruting serwisu autentykacji
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccessRightRepository accessRightRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    /**
     * Funkcja odpowiadajaca za logowanie użytkownika
     * @param loginRequest
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    /**
     * Funkcja odpowiedzialna za rejestracje użytkownika
     * @param signUpRequest
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRole();
        Set<AccessRight> accessRights = new HashSet<>();
        if (strRoles == null) {
            AccessRight userAccessRight1 = accessRightRepository.save(new AccessRight(EnumAccessRight.USER_ACCESS_RIGHT)); //roleRepository.findByName(ERole.ROLE_USER).or(new Role(ERole.ROLE_USER))
                    //.orElseThrow(() -> new RuntimeException("Error: Role is not found 1."));
            //AccessRight userAccessRight2 = accessRightRepository.save(new AccessRight(EnumAccessRight.MODERATOR_ACCESS_RIGHT));
            accessRights.add(userAccessRight1);
            //accessRights.add(userAccessRight2);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        AccessRight adminAccessRight = accessRightRepository.findByName(EnumAccessRight.ADMIN_ACCESS_RIGHT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found admin."));
                        accessRights.add(adminAccessRight);
                        break;
                    case "mod":
                        AccessRight modAccessRight = accessRightRepository.findByName(EnumAccessRight.MODERATOR_ACCESS_RIGHT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found mod."));
                        accessRights.add(modAccessRight);
                        break;
                    default:
                        AccessRight userAccessRight = accessRightRepository.findByName(EnumAccessRight.USER_ACCESS_RIGHT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found user."));
                        accessRights.add(userAccessRight);
                }
            });
        }
        user.setRoles(accessRights);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    /**
     * Dodanie roli do bazy danych
     * @return
     */
    @GetMapping("/addroles")
    public ResponseEntity<?> addRoles() {
        accessRightRepository.save(new AccessRight(EnumAccessRight.USER_ACCESS_RIGHT));
        accessRightRepository.save(new AccessRight(EnumAccessRight.ADMIN_ACCESS_RIGHT));
        AccessRight accessRight = accessRightRepository.save(new AccessRight(EnumAccessRight.MODERATOR_ACCESS_RIGHT));
        return new ResponseEntity<>(accessRight, HttpStatus.CREATED);
    }
}
