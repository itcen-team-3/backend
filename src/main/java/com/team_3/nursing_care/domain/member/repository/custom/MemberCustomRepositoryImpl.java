package com.team_3.nursing_care.domain.member.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team_3.nursing_care.domain.member.constant.Role;
import com.team_3.nursing_care.domain.member.dto.response.AccountListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.team_3.nursing_care.domain.member.entity.QMember.member;
import static com.team_3.nursing_care.domain.member.entity.QPatientInfo.patientInfo;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<AccountListResponseDto> getAccountList(Long companyId, String searchName, String searchRole, Pageable pageable) {
        Role role;

        BooleanBuilder whereClause = new BooleanBuilder();
        whereClause.and(member.company.companyId.eq(companyId));
        whereClause.and(member.accountIsDeleted.eq(false));
        whereClause.and(member.isDeleted.eq(false));
        whereClause.and(member.loginId.isNotNull());

        if (searchName != null && !searchName.isBlank()) {
            whereClause.and(
                    member.memberName.contains(searchName)
                            .or(member.loginId.contains(searchName))
            );
        }

        if (searchRole != null) {
            try {
                role = Role.valueOf(searchRole.toUpperCase());

                if (role != null) {
                    whereClause.and(member.role.eq(role));
                }

            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("존재하지 않는 권한입니다: " + searchRole);
            }
        }

        List<AccountListResponseDto> content = queryFactory
                .selectFrom(member)
                .leftJoin(member.patientInfo, patientInfo).fetchJoin()
                .where(whereClause)
                .orderBy(getOrderBy(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(AccountListResponseDto::create)
                .toList();

        JPAQuery<Long> countQuery = queryFactory.select(member.count())
                .from(member)
                .where(whereClause);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?>[] getOrderBy(Sort sort) {
        return sort.stream()
                .map(order -> {
                    if (order.getProperty().equals("loginId")) {
                        return order.isAscending() ? member.loginId.asc() : member.loginId.desc();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toArray(OrderSpecifier[]::new);
    }
}
