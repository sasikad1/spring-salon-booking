package com.sasika.salon.booking.config;

import com.sasika.salon.booking.service.SlotService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private SlotService slotService;

//    automate this api work after deploy the application
    @PostConstruct
    public void onStartup() {
        slotService.initializeSlotsForFiveDays();
    }

    @Scheduled(cron = "0 0 22 * * ?") // Every day at 10 PM | second min hour daily everyMonth(0 0 22 * *)
    public void runSlotCycle() {
        slotService.generateNextDaySlotAfterCycle();
        slotService.deleteSlotsForDate(LocalDate.now().minusDays(1));
    }
}

