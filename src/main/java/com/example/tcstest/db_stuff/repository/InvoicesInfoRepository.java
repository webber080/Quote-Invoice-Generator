package com.example.tcstest.db_stuff.repository;

import com.example.tcstest.db_stuff.entity.InvoicesInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoicesInfoRepository extends JpaRepository<InvoicesInfo, Integer> {
    //
}
