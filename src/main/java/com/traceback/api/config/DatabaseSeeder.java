package com.traceback.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.traceback.api.repository.ClaimRepository;
import com.traceback.api.repository.ItemRepository;
import com.traceback.api.repository.UserRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired private UserRepository userRepository;
    @Autowired private ItemRepository itemRepository;
    @Autowired private ClaimRepository claimRepository;
    @Autowired private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
//        if (userRepository.count() > 0) return; // Don't double-seed
//
//        // 1. CREATE MOCK USERS
//        User rahul = User.builder().name("Rahul Sharma").email("rahul@test.com").passwordHash(passwordEncoder.encode("password")).build();
//        User anjali = User.builder().name("Anjali Gupta").email("anjali@test.com").passwordHash(passwordEncoder.encode("password")).build();
//        User admin = User.builder().name("Admin Trace").email("admin@traceback.com").passwordHash(passwordEncoder.encode("password")).build();
//        userRepository.saveAll(List.of(rahul, anjali, admin));
//
//        // 2. CREATE REPORTED ITEMS (Some Found, Some Lost)
//        Item item1 = Item.builder()
//                .title("Blue Nike Backpack")
//                .description("Found near the library. Contains some notebooks and a water bottle.")
//                .category("Accessories")
//                .location("Central Library")
//                .status("FOUND")
//                .imageUrl("https://images.unsplash.com/photo-1553062407-98eeb64c6a62")
//                .finder(rahul).build();
//
//        Item item2 = Item.builder()
//                .title("MacBook Air M4")
//                .description("Silver color, left in the cafeteria. Has a 'Developer' sticker.")
//                .category("Electronics")
//                .location("Main Cafeteria")
//                .status("FOUND")
//                .imageUrl("https://images.unsplash.com/photo-1517336714731-489689fd1ca8")
//                .finder(anjali).build();
//
//        Item item3 = Item.builder()
//                .title("Golden Retriever (Puppy)")
//                .description("Found wandering in the park. Very friendly.")
//                .category("Pets")
//                .location("City Park")
//                .status("RESOLVED") // Already returned
//                .imageUrl("https://images.unsplash.com/photo-1552053831-71594a27632d")
//                .finder(admin).build();
//
//        itemRepository.saveAll(List.of(item1, item2, item3));
//
//        // 3. CREATE CLAIMS
//        // Anjali claims Rahul's backpack
//        Claim claim1 = Claim.builder()
//                .proofDescription("It has a red water bottle inside and my name is written on the first notebook.")
//                .status("PENDING")
//                .item(item1)
//                .loser(anjali).build();
//
//        // Rahul claims Anjali's MacBook
//        Claim claim2 = Claim.builder()
//                .proofDescription("The developer sticker is from a React conference I attended.")
//                .status("APPROVED") // This one is approved
//                .item(item2)
//                .loser(rahul).build();
//
//        claimRepository.saveAll(List.of(claim1, claim2));
        
//        System.out.println("✅ TiDB successfully seeded with mock data!");
    }
}