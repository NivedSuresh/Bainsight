package org.bainsight.portfolio.Data;


import org.bainsight.portfolio.Model.Entity.Portfolio;
import org.bainsight.portfolio.Model.Entity.PortfolioSymbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
interface PortfolioRepo extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findByUcc(UUID ucc);

    @Query("SELECT ps FROM PortfolioSymbol ps JOIN ps.portfolio p WHERE p.ucc = :ucc AND ps.symbol = :symbol")
    Optional<PortfolioSymbol> fetchPortfolioSymbol(@Param("ucc") UUID ucc, String symbol);



    @Query("select p.portfolioId from Portfolio as p where p.ucc =:ucc")
    Long findPortfolioIdByUcc(UUID ucc);

}
