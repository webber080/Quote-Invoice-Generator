package com.example.tcstest.db_stuff.controller;

import com.example.tcstest.db_stuff.entity.InvoicesInfo;
import com.example.tcstest.db_stuff.entity.Users;
import com.example.tcstest.db_stuff.service.InvoicesInfoService;
import com.example.tcstest.db_stuff.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoices")
public class InvoicesInfoController {
    private final InvoicesInfoService invoicesInfoService;

    @Autowired
    public InvoicesInfoController(InvoicesInfoService invoicesInfoService) {
        this.invoicesInfoService = invoicesInfoService;
    }


    @PostMapping // Refers to http://localhost:8080/invoices/
    public InvoicesInfo createInvoice(@RequestBody InvoicesInfo invoicesInfo) { // '@RequestBody' takes the content of the body sent to this path
        return invoicesInfoService.createInvoice(invoicesInfo);
    }
}
