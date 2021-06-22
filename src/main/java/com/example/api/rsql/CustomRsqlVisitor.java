package com.example.api.rsql;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import org.springframework.data.jpa.domain.Specification;

public class CustomRsqlVisitor<T> implements RSQLVisitor<Specification<T>, Void> {

    private final GenericRsqlSpecBuilder<T> BUILDER;


    public CustomRsqlVisitor() {
        BUILDER = new GenericRsqlSpecBuilder<T>();
    }

    @Override
    public Specification<T> visit(AndNode node, Void param) {
        return BUILDER.createSpecification(node);
    }

    @Override
    public Specification<T> visit(OrNode node, Void param) {
        return BUILDER.createSpecification(node);
    }

    @Override
    public Specification<T> visit(ComparisonNode node, Void param) {
        return BUILDER.createSpecification(node);
    }
}
