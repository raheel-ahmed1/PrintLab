package com.PrintLab.repository;

import com.PrintLab.model.PaperMarketRates;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface PaperMarketRatesRepository extends JpaRepository<PaperMarketRates,Long> {
    PaperMarketRates findByPaperStock(String paperStock);
    @Query("SELECT p FROM PaperMarketRates p WHERE p.paperStock = :paperStock ORDER BY p.timeStamp DESC")
    List<PaperMarketRates> findAllPaperMarketRatesByPaperStockOrderByTimestampDesc(@Param("paperStock") String paperStock);
    @Query("SELECT DISTINCT p.paperStock FROM PaperMarketRates p")
    Set<String> findDistinctPaperStocks();
    @Query("SELECT DISTINCT p.brand FROM PaperMarketRates p WHERE p.paperStock = :paperStock")
    Set<String> findDistinctBrandsByPaperStock(@Param("paperStock") String paperStock);
    @Query("SELECT DISTINCT p.madeIn FROM PaperMarketRates p WHERE p.paperStock = :paperStock AND p.brand = :brand")
    Set<String> findDistinctMadeInByPaperStockAndBrand(@Param("paperStock") String paperStock, @Param("brand") String brand);
    @Query("SELECT DISTINCT p.dimension FROM PaperMarketRates p WHERE p.paperStock = :paperStock AND brand = :brand AND madeIn = :madeIn")
    Set<String> findDistinctDimensionByPaperStockAndBrandAndMadeIn(@Param("paperStock") String paperStock,
                                                                @Param("brand") String brand,
                                                                @Param("madeIn") String madeIn);

    @Query("SELECT DISTINCT p.vendor FROM PaperMarketRates p WHERE p.paperStock = :paperStock AND brand = :brand AND madeIn = :madeIn AND dimension = :dimension")
    Set<Long> findDistinctVendorByPaperStockAndBrandAndMadeInAndDimension(@Param("paperStock") String paperStock,
                                                                            @Param("brand") String brand,
                                                                            @Param("madeIn") String madeIn,
                                                                            @Param("dimension") String dimension);
    @Query("SELECT DISTINCT p.GSM FROM PaperMarketRates p WHERE p.paperStock = :paperStock AND brand = :brand AND madeIn = :madeIn And dimension = :dimension AND vendor = :vendorId")
    Set<String> findDistinctGsmByPaperStockAndBrandAndMadeInAndDimensionAndVendor(@Param("paperStock") String paperStock,
                                                                                  @Param("brand") String brand,
                                                                                  @Param("madeIn") String madeIn,
                                                                                  @Param("dimension") String dimension,
                                                                                  @Param("vendorId") Long vendorId);

    @Query("SELECT pmr FROM PaperMarketRates pmr WHERE pmr.paperStock = :paperStock AND pmr.vendor = :vendorId AND pmr.brand = :brand AND pmr.madeIn = :madeIn AND pmr.dimension = :dimension AND pmr.GSM IN :gsm AND pmr.status = 'In Stock'")
    List<PaperMarketRates> findPaperMarketRateByEveryColumn(@Param("paperStock") String paperStock,
                                                      @Param("vendorId") Long vendorId,
                                                      @Param("brand") String brand,
                                                      @Param("madeIn") String madeIn,
                                                      @Param("dimension") String dimension,
                                                      @Param("gsm") List<Integer> gsm);

    PaperMarketRates findByPaperStockAndGSMAndDimension(String paperStock, Integer gsm, String dimension);
    @Query("SELECT pmr FROM PaperMarketRates pmr WHERE pmr.paperStock LIKE %:searchName%")
    List<PaperMarketRates> findPaperMarketRatesByPaperStock(@Param("searchName") String searchName);
    List<PaperMarketRates> findByPaperStockAndGSMOrderByTimeStampDesc(String paperStock, Integer gsm);
    @Query("SELECT DISTINCT pmr.GSM FROM PaperMarketRates pmr WHERE pmr.paperStock = :paperStock")
    List<Integer> findDistinctGSMByPaperStock(@Param("paperStock") String paperStock);

    @Query(value = "SELECT " +
            "GROUP_CONCAT(DISTINCT paper_stock) AS paper_stock, " +
            "GROUP_CONCAT(DISTINCT brand) AS brand, " +
            "GROUP_CONCAT(DISTINCT made_in) AS made_in, " +
            "GROUP_CONCAT(DISTINCT gsm) AS gsm, " +
            "GROUP_CONCAT(DISTINCT dimension) AS dimension, " +
            "GROUP_CONCAT(DISTINCT qty) AS qty, " +
            "GROUP_CONCAT(DISTINCT record_type) AS record_type, " +
            "GROUP_CONCAT(DISTINCT status) AS status, " +
            "GROUP_CONCAT(DISTINCT vendor) AS vendor " +
            "FROM paper_market_rates",
            nativeQuery = true)
    Map<String, String> findAllDistinctValues();
}
