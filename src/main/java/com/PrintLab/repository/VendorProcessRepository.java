package com.PrintLab.repository;

import com.PrintLab.model.VendorProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorProcessRepository extends JpaRepository<VendorProcess,Long> {
}
