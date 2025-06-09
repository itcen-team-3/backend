package com.team_3.nursing_care.domain.care_log.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team_3.nursing_care.domain.care_log.dto.response.ResCareLogDto;
import com.team_3.nursing_care.domain.care_log.entity.CareLog;
import com.team_3.nursing_care.domain.care_log.entity.QCareDetail;
import com.team_3.nursing_care.domain.care_log.entity.QCareLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.team_3.nursing_care.domain.care_log.entity.QCareDetail.*;
import static com.team_3.nursing_care.domain.care_log.entity.QCareLog.*;
import static com.team_3.nursing_care.domain.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class CareLogCustomRepositoryImpl implements CareLogCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ResCareLogDto> getCareLogPage(LocalDate date, Pageable pageable, Long memberId) {

        BooleanBuilder whereClause = new BooleanBuilder();
        whereClause.and(careLog.careGiver.memberId.eq(memberId));
        whereClause.and(careLog.isDeleted.eq(false));

        if (date != null) {
            whereClause.and(careLog.createDate.year().eq(date.getYear()));
            whereClause.and(careLog.createDate.month().eq(date.getMonthValue()));
            whereClause.and(careLog.createDate.dayOfMonth().eq(date.getDayOfMonth()));
        }

        List<ResCareLogDto> content = queryFactory.select(careLog)
                .from(careLog)
                .leftJoin(careLog.careDetailList, careDetail).fetchJoin()
                .leftJoin(careLog.careGiver, member).fetchJoin()
                .where(whereClause)
                .orderBy(getOrderBy(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(ResCareLogDto::create)
                .toList();

        JPAQuery<Long> countQuery = queryFactory.select(careLog.count())
                .from(careLog)
                .leftJoin(careLog.careDetailList, careDetail).fetchJoin()
                .leftJoin(careLog.careGiver, member).fetchJoin()
                .where(whereClause);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?>[] getOrderBy(Sort sort) {
        return sort.stream()
                .map(order -> {
                    if (order.getProperty().equals("date")) {
                        return order.isAscending() ? careLog.createDate.asc() : careLog.createDate.desc();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toArray(OrderSpecifier[]::new);
    }
}
