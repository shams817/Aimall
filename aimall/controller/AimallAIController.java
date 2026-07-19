package com.aimall.aimall.controller;

import com.aimall.aimall.service.AimallAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AimallAIController {

    @Autowired
    private AimallAIService aimallAIService;

    /**
     * Process user query and get AI response
     * POST /api/ai/chat
     * Body: { "query": "user question", "userId": 1 }
     */
    @PostMapping("/chat")
    public ResponseEntity<?> chat(@RequestBody Map<String, Object> request) {
        try {
            String query = (String) request.get("query");
            Long userId = null;
            
            if (request.get("userId") != null) {
                userId = ((Number) request.get("userId")).longValue();
            }

            String response = aimallAIService.processQuery(query, userId);
            return ResponseEntity.ok(Map.of(
                "response", response,
                "timestamp", System.currentTimeMillis(),
                "aiName", "VIYA"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Error processing query",
                "message", e.getMessage()
            ));
        }
    }

    /**
     * Get AI greeting based on time of day
     * GET /api/ai/greeting
     */
    @GetMapping("/greeting")
    public ResponseEntity<?> getGreeting() {
        String greeting = aimallAIService.getGreeting();
        return ResponseEntity.ok(Map.of(
            "greeting", greeting,
            "aiName", "VIYA"
        ));
    }

    /**
     * Get shopping tips from AI
     * GET /api/ai/tips
     */
    @GetMapping("/tips")
    public ResponseEntity<?> getTips() {
        String tips = aimallAIService.getShoppingTips();
        return ResponseEntity.ok(Map.of(
            "tips", tips,
            "aiName", "VIYA"
        ));
    }

    /**
     * Get information about VIYA AI
     * GET /api/ai/about
     */
    @GetMapping("/about")
    public ResponseEntity<?> getAbout() {
        String about = aimallAIService.getAboutVIYA();
        return ResponseEntity.ok(Map.of(
            "about", about,
            "aiName", "VIYA"
        ));
    }

    /**
     * Process voice query (text converted from speech)
     * POST /api/ai/voice
     * Body: { "text": "speech converted text", "userId": 1 }
     */
    @PostMapping("/voice")
    public ResponseEntity<?> processVoiceQuery(@RequestBody Map<String, Object> request) {
        try {
            String text = (String) request.get("text");
            Long userId = null;
            
            if (request.get("userId") != null) {
                userId = ((Number) request.get("userId")).longValue();
            }

            // Process voice query same as text query
            String response = aimallAIService.processQuery(text, userId);
            return ResponseEntity.ok(Map.of(
                "response", response,
                "text", text,
                "timestamp", System.currentTimeMillis(),
                "aiName", "VIYA"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Error processing voice query",
                "message", e.getMessage()
            ));
        }
    }
}
