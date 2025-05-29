package com.xerox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XeroxRentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(XeroxRentalApplication.class, args);
	}
//	@Bean
// public CommandLineRunner initAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//     return args -> {
//         if (userRepository.findByUsername("admin1").isEmpty()) {
//             User admin = new User();
//             admin.setUsername("admin");
//          admin.setEmail("admin1@example.com");
//            admin.setPassword(passwordEncoder.encode("admin@123"));             
//            admin.setRole("ROLE_ADMIN");
//            userRepository.save(admin);
//       }
//     };
// }

}
