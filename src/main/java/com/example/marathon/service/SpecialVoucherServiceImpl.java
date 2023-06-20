package com.example.marathon.service;

import com.example.marathon.dataobject.SpecialVoucher;
import com.example.marathon.dataobject.Voucher;
import com.example.marathon.mapper.SpecialVoucherMapper;
import com.example.marathon.mapper.VoucherMapper;
import com.example.marathon.util.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class SpecialVoucherServiceImpl implements SpecialVoucherService {
    private final VoucherMapper voucherMapper;
    private final SpecialVoucherMapper specialVoucherMapper;
    private final IdGenerator idGenerator;

    public SpecialVoucherServiceImpl(VoucherMapper voucherMapper, SpecialVoucherMapper specialVoucherMapper, IdGenerator idGenerator) {
        this.voucherMapper = voucherMapper;
        this.specialVoucherMapper = specialVoucherMapper;
        this.idGenerator = idGenerator;
    }

    @Override
    public boolean insertVoucher(Voucher voucher) {
        boolean flag = true;
        Long id = idGenerator.nextId(voucher.getTitle());
        voucher.setId(id);
        if (voucher.getType() == 1) {
            SpecialVoucher specialVoucher = new SpecialVoucher();
            specialVoucher.setVoucherId(id);
            BeanUtils.copyProperties(voucher, specialVoucher);
            flag = specialVoucherMapper.insert(specialVoucher) > 0;
        }
        return flag && voucherMapper.insert(voucher) > 0;
    }
}

