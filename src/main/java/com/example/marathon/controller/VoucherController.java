package com.example.marathon.controller;

import com.example.marathon.dataobject.SpecialVoucher;
import com.example.marathon.dataobject.Voucher;
import com.example.marathon.service.SpecialVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/voucher")
public class VoucherController {
    private final SpecialVoucherService specialVoucherService;

    @Autowired
    public VoucherController(SpecialVoucherService specialVoucherService) {
        this.specialVoucherService = specialVoucherService;
    }

    @PostMapping("/insert")
    public String createSpecialVoucher(@RequestBody Voucher voucher) {
        boolean result = specialVoucherService.insertVoucher(voucher);
        if (result) {
            return "Special voucher created successfully";
        } else {
            return "Failed to create special voucher";
        }
    }
}

