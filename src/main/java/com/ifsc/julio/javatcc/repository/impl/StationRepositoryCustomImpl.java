package com.ifsc.julio.javatcc.repository.impl;

import com.ifsc.julio.javatcc.dto.FiltroStationDTO;
import com.ifsc.julio.javatcc.entity.QStationEntity;
import com.ifsc.julio.javatcc.entity.StationEntity;
import com.ifsc.julio.javatcc.repository.StationRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import static java.util.Objects.*;

public class StationRepositoryCustomImpl implements StationRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    private final QStationEntity qStationEntity = QStationEntity.stationEntity;

    @Override
    public List<StationEntity> findAllWithFilters(FiltroStationDTO filtroStationDTO) {
        BooleanBuilder where = new BooleanBuilder();

        if (nonNull(filtroStationDTO.getIbge())) {
            where.and(qStationEntity.ibge.eq(filtroStationDTO.getIbge()));
        } else if (nonNull(filtroStationDTO.getUf())) {
            where.and(qStationEntity.uf.eq(filtroStationDTO.getUf()));
        } else if (nonNull(filtroStationDTO.getRegion())) {
            where.and(qStationEntity.uf.in(filtroStationDTO.getRegion().getStates()));
        }

        return new JPAQueryFactory(em)
                .selectFrom(qStationEntity)
                .where(where)
                .fetch();
    }
}
