package com.sasika.salon.booking.controller;

import com.sasika.salon.booking.dto.BranchDto;
import com.sasika.salon.booking.dto.WorkingHoursDto;
import com.sasika.salon.booking.entity.Branch;
import com.sasika.salon.booking.entity.WorkingHours;
import com.sasika.salon.booking.service.BranchService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/branches")
@CrossOrigin("*")
public class BranchController {
    private final   BranchService branchService;
    private final ModelMapper modelMapper;

    public BranchController(BranchService branchService, ModelMapper modelMapper) {
        this.branchService = branchService;
        this.modelMapper = modelMapper;
    }

    private Branch convertToEntity(BranchDto branchDto) {
        Branch branch = modelMapper.map(branchDto, Branch.class);

        if (branchDto.getWorkingHours() != null) {
            List<WorkingHours> hours = branchDto.getWorkingHours().stream().map(dto -> {
                WorkingHours wh = new WorkingHours();
                wh.setDayOfWeek(dto.getDayOfWeek());
                wh.setClosed(dto.isClosed());

                if (!dto.isClosed()) {
                    wh.setOpenTime(LocalTime.parse(dto.getOpenTime()));
                    wh.setCloseTime(LocalTime.parse(dto.getCloseTime()));
                }

                wh.setBranch(branch); // important
                return wh;
            }).collect(Collectors.toList());

            branch.setWorkingHours(new HashSet<>(hours));

        }

        return branch;
    }


    private BranchDto convertDto(Branch branch) {
        BranchDto dto = modelMapper.map(branch, BranchDto.class);

        if (branch.getWorkingHours() != null) {
            List<WorkingHoursDto> hours = branch.getWorkingHours().stream().map(wh -> {
                return WorkingHoursDto.builder()
                        .dayOfWeek(wh.getDayOfWeek())
                        .openTime(wh.getOpenTime() != null ? wh.getOpenTime().toString() : null)
                        .closeTime(wh.getCloseTime() != null ? wh.getCloseTime().toString() : null)
                        .closed(wh.isClosed())
                        .build();
            }).collect(Collectors.toList());

            dto.setWorkingHours(hours);
        }

        return dto;
    }



//    private BranchDto convertDto(Branch branch){
//        return modelMapper.map(branch, BranchDto.class);
//    }
//
//    private Branch convertToEntity(BranchDto branchDto){
//        return modelMapper.map(branchDto, Branch.class);
//    }

    @GetMapping
    public ResponseEntity<List<BranchDto>> getAllBranches(){
        List<BranchDto> branchDtos = branchService.getAllBranches()
                .stream()
                .map(this::convertDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(branchDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDto> getBranchById(@PathVariable Long id){
        Branch branch = branchService.getBranchById(id);
        return ResponseEntity.ok(convertDto(branch));
    }

    @PostMapping
    public ResponseEntity<BranchDto> createBranch(@Valid @RequestBody BranchDto branchDto){
        Branch branch = branchService.createBranch(convertToEntity(branchDto));
        return ResponseEntity.ok(convertDto(branch));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDto> updateBranch(@PathVariable Long id, @Valid @RequestBody BranchDto branchDto){
        Branch branch = branchService.updateBranch(id, convertToEntity(branchDto));
        return ResponseEntity.ok(convertDto(branch));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBranch(@PathVariable Long id){
        String result = branchService.deleteBranch(id);
        if(result.contains("Branch not found")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Branch not found");
        }
        return ResponseEntity.ok(result);
    }
}
