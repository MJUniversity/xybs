package com.expert.xybs.pojo;

import java.util.ArrayList;
import java.util.List;

public class LabelsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LabelsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andIocIsNull() {
            addCriterion("ioc is null");
            return (Criteria) this;
        }

        public Criteria andIocIsNotNull() {
            addCriterion("ioc is not null");
            return (Criteria) this;
        }

        public Criteria andIocEqualTo(String value) {
            addCriterion("ioc =", value, "ioc");
            return (Criteria) this;
        }

        public Criteria andIocNotEqualTo(String value) {
            addCriterion("ioc <>", value, "ioc");
            return (Criteria) this;
        }

        public Criteria andIocGreaterThan(String value) {
            addCriterion("ioc >", value, "ioc");
            return (Criteria) this;
        }

        public Criteria andIocGreaterThanOrEqualTo(String value) {
            addCriterion("ioc >=", value, "ioc");
            return (Criteria) this;
        }

        public Criteria andIocLessThan(String value) {
            addCriterion("ioc <", value, "ioc");
            return (Criteria) this;
        }

        public Criteria andIocLessThanOrEqualTo(String value) {
            addCriterion("ioc <=", value, "ioc");
            return (Criteria) this;
        }

        public Criteria andIocLike(String value) {
            addCriterion("ioc like", value, "ioc");
            return (Criteria) this;
        }

        public Criteria andIocNotLike(String value) {
            addCriterion("ioc not like", value, "ioc");
            return (Criteria) this;
        }

        public Criteria andIocIn(List<String> values) {
            addCriterion("ioc in", values, "ioc");
            return (Criteria) this;
        }

        public Criteria andIocNotIn(List<String> values) {
            addCriterion("ioc not in", values, "ioc");
            return (Criteria) this;
        }

        public Criteria andIocBetween(String value1, String value2) {
            addCriterion("ioc between", value1, value2, "ioc");
            return (Criteria) this;
        }

        public Criteria andIocNotBetween(String value1, String value2) {
            addCriterion("ioc not between", value1, value2, "ioc");
            return (Criteria) this;
        }

        public Criteria andLxIsNull() {
            addCriterion("lx is null");
            return (Criteria) this;
        }

        public Criteria andLxIsNotNull() {
            addCriterion("lx is not null");
            return (Criteria) this;
        }

        public Criteria andLxEqualTo(Integer value) {
            addCriterion("lx =", value, "lx");
            return (Criteria) this;
        }

        public Criteria andLxNotEqualTo(Integer value) {
            addCriterion("lx <>", value, "lx");
            return (Criteria) this;
        }

        public Criteria andLxGreaterThan(Integer value) {
            addCriterion("lx >", value, "lx");
            return (Criteria) this;
        }

        public Criteria andLxGreaterThanOrEqualTo(Integer value) {
            addCriterion("lx >=", value, "lx");
            return (Criteria) this;
        }

        public Criteria andLxLessThan(Integer value) {
            addCriterion("lx <", value, "lx");
            return (Criteria) this;
        }

        public Criteria andLxLessThanOrEqualTo(Integer value) {
            addCriterion("lx <=", value, "lx");
            return (Criteria) this;
        }

        public Criteria andLxIn(List<Integer> values) {
            addCriterion("lx in", values, "lx");
            return (Criteria) this;
        }

        public Criteria andLxNotIn(List<Integer> values) {
            addCriterion("lx not in", values, "lx");
            return (Criteria) this;
        }

        public Criteria andLxBetween(Integer value1, Integer value2) {
            addCriterion("lx between", value1, value2, "lx");
            return (Criteria) this;
        }

        public Criteria andLxNotBetween(Integer value1, Integer value2) {
            addCriterion("lx not between", value1, value2, "lx");
            return (Criteria) this;
        }

        public Criteria andPxIsNull() {
            addCriterion("px is null");
            return (Criteria) this;
        }

        public Criteria andPxIsNotNull() {
            addCriterion("px is not null");
            return (Criteria) this;
        }

        public Criteria andPxEqualTo(Integer value) {
            addCriterion("px =", value, "px");
            return (Criteria) this;
        }

        public Criteria andPxNotEqualTo(Integer value) {
            addCriterion("px <>", value, "px");
            return (Criteria) this;
        }

        public Criteria andPxGreaterThan(Integer value) {
            addCriterion("px >", value, "px");
            return (Criteria) this;
        }

        public Criteria andPxGreaterThanOrEqualTo(Integer value) {
            addCriterion("px >=", value, "px");
            return (Criteria) this;
        }

        public Criteria andPxLessThan(Integer value) {
            addCriterion("px <", value, "px");
            return (Criteria) this;
        }

        public Criteria andPxLessThanOrEqualTo(Integer value) {
            addCriterion("px <=", value, "px");
            return (Criteria) this;
        }

        public Criteria andPxIn(List<Integer> values) {
            addCriterion("px in", values, "px");
            return (Criteria) this;
        }

        public Criteria andPxNotIn(List<Integer> values) {
            addCriterion("px not in", values, "px");
            return (Criteria) this;
        }

        public Criteria andPxBetween(Integer value1, Integer value2) {
            addCriterion("px between", value1, value2, "px");
            return (Criteria) this;
        }

        public Criteria andPxNotBetween(Integer value1, Integer value2) {
            addCriterion("px not between", value1, value2, "px");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}