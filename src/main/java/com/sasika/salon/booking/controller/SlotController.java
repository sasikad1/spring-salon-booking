package com.sasika.salon.booking.controller;

import com.sasika.salon.booking.entity.Slot;
import com.sasika.salon.booking.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/slots")
@CrossOrigin("*")
public class SlotController {

    private final SlotService slotService;

    @Autowired
    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    /**
     * 🔍 Get slots by date
     * Example: GET /api/slots?date=2025-07-01
     */
    @GetMapping
    public ResponseEntity<List<Slot>> getSlotsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Slot> slots = slotService.getSlotsByDate(date);
        return ResponseEntity.ok(slots);
    }

    /**
     * ⚙️ Manually generate slots for a specific date
     * Example: POST /api/slots/generate?date=2025-07-01
     */
    @PostMapping("/generate")
    public ResponseEntity<String> generateSlots(@RequestBody Map<String, String> request) {
        LocalDate date = LocalDate.parse(request.get("date"));
        String message = slotService.generateSlotsForDate(date);
        return ResponseEntity.ok("Slot Generation Completed:\n" + message);
    }

    /**
     * ❌ Delete slots by date
     * Example: DELETE /api/slots?date=2025-06-25
     */
    @DeleteMapping
    public ResponseEntity<String> deleteSlotsForDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        slotService.deleteSlotsForDate(date);
        return ResponseEntity.ok("Deleted slots for: " + date);
    }

    /**
     * ❗ Delete all slots - optional
     * Example: DELETE /api/slots/all
     */
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllSlots() {
        slotService.deleteAllSlots();
        return ResponseEntity.ok("All slots deleted");
    }
}
