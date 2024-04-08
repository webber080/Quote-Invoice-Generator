package com.example.tcstest.db_stuff.service;

import com.example.tcstest.db_stuff.entity.InvoicesInfo;
import com.example.tcstest.db_stuff.entity.Users;
import com.example.tcstest.db_stuff.repository.InvoicesInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoicesInfoService {
    private final InvoicesInfoRepository invoicesInfoRepository;

    @Autowired
    public InvoicesInfoService(InvoicesInfoRepository invoicesInfoRepository) {
        this.invoicesInfoRepository = invoicesInfoRepository;
    }

    public InvoicesInfo createInvoice(InvoicesInfo invoicesInfo) {
        return invoicesInfoRepository.save(invoicesInfo);
    }

    public List<InvoicesInfo> getAllInvoices(){
        return invoicesInfoRepository.findAll();
    }
}
