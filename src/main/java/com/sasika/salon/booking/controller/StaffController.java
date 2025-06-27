package com.sasika.salon.booking.controller;

import com.sasika.salon.booking.dto.StaffDto;
import com.sasika.salon.booking.entity.Branch;
import com.sasika.salon.booking.entity.Staff;
import com.sasika.salon.booking.service.BranchService;
import com.sasika.salon.booking.service.StaffService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin(origins = "*")
public class StaffController {
    private final StaffService staffService;
    private final BranchService branchService;
    private final ModelMapper modelMapper;
    public StaffController(StaffService staffService, BranchService branchService, ModelMapper modelMapper) {
        this.staffService = staffService;
        this.branchService = branchService;
        this.modelMapper =  modelMapper;
    }

    private StaffDto convertToDto(Staff staff) {
        StaffDto dto = modelMapper.map(staff, StaffDto.class);
        dto.setBranchId(staff.getBranch().getId());
        return dto;
    }

    private Staff convertToEntity(StaffDto dto) {
        Staff staff = modelMapper.map(dto, Staff.class);
        Branch branch = branchService.getBranchById(dto.getBranchId()); // load Branch by ID
        staff.setBranch(branch);
        return staff;
    }

    @GetMapping
    public ResponseEntity<List<StaffDto>> getAllStaff() {
        List<StaffDto> list = staffService.getAllStaff()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffDto> getStaffById(@PathVariable Long id) {
        Staff staff = staffService.getStaffById(id);
        return ResponseEntity.ok(convertToDto(staff));
    }

    @PostMapping
    public ResponseEntity<StaffDto> createStaff(@Valid @RequestBody StaffDto staffDto) {
        Staff staff = staffService.createStaff(convertToEntity(staffDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(staff));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffDto> updateStaff(@PathVariable Long id, @Valid @RequestBody StaffDto staffDto) {
        Staff staff = staffService.updateStaff(id, convertToEntity(staffDto));
        return ResponseEntity.ok(convertToDto(staff));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStaff(@PathVariable Long id){
        String result = staffService.deleteStaff(id);
        if(result.contains("not found")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }
}
