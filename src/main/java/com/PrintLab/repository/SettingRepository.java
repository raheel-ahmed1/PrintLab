package com.PrintLab.repository;

import com.PrintLab.model.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettingRepository extends JpaRepository<Setting,Long> {
    Setting findByKey(String key);
    @Query("SELECT s FROM Setting s WHERE s.key LIKE %:searchKey%")
    List<Setting> findSettingsByKey(@Param("searchKey") String searchKey);
}
