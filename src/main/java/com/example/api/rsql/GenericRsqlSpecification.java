package com.example.api.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GenericRsqlSpecification<T> implements Specification<T> {
    private final String property;
    private final ComparisonOperator operator;
    private final List<String> arguments;

    public GenericRsqlSpecification(
            String property, ComparisonOperator operator, List<String> arguments) {
        super();
        this.property = property;
        this.operator = operator;
        this.arguments = arguments;
    }

    @Override
    public Predicate toPredicate(
            Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Object> args = castArguments(root);
        Object argument = args.get(0);
        switch (Objects.requireNonNull(RsqlSearchOperation.getSimpleOperator(operator))) {

            case EQUAL: {
                if (argument instanceof String) {
                    return builder.like(builder.lower(root.get(property)), '%' + argument.toString().replace('*', '%') + '%');
                } else if (argument == null) {
                    return builder.isNull(root.get(property));
                } else {
                    return builder.equal(root.get(property), argument);
                }
            }
            case NOT_EQUAL: {
                if (argument instanceof String) {
                    return builder.notLike(builder.lower(root.get(property)), '%' + argument.toString().replace('*', '%') + '%');

                } else if (argument == null) {
                    return builder.isNotNull(root.get(property));
                } else {
                    return builder.notEqual(root.get(property), argument);
                }
            }
            case GREATER_THAN: {
                return builder.greaterThan(root.get(property), argument.toString());
            }
            case GREATER_THAN_OR_EQUAL: {
                return builder.greaterThanOrEqualTo(
                        root.get(property), argument.toString());
            }
            case LESS_THAN: {
                return builder.lessThan(root.get(property), argument.toString());
            }
            case LESS_THAN_OR_EQUAL: {
                return builder.lessThanOrEqualTo(
                        root.get(property), argument.toString());
            }
            case IN:
                return root.get(property).in(args);
            case NOT_IN:
                return builder.not(root.get(property).in(args));
        }

        return null;
    }


    private List<Object> castArguments(Root<T> root) {
        List<Object> args = new ArrayList<>();
        Class<? extends Object> type = root.get(property).getJavaType();

        for (String argument : arguments) {
            if (type.equals(Integer.class)) {
                args.add(Integer.parseInt(argument));
            } else if (type.equals(Long.class)) {
                args.add(Long.parseLong(argument));
            } else {
                args.add(argument);
            }
        }

        return args;
    }
}
