package com.PrintLab.repository;

import com.PrintLab.model.ProductRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRuleRepository extends JpaRepository<ProductRule,Long> {
    ProductRule findByTitleAndStatusIsTrue (String productValue);
    List<ProductRule> findProductRuleByTitle(String title);
    List<ProductRule> findAllByStatusIsTrue();

    @Modifying
    @Query("UPDATE ProductRule pr SET pr.status = false WHERE pr.id = :id")
    void setStatusInactive(@Param("id") Long id);

    Boolean existsByTitleAndStatusIsTrue(String title);
}
