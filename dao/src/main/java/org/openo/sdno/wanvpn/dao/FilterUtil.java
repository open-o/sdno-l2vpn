/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.wanvpn.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.openo.sdno.wanvpn.dao.FilterUtil;
import org.springframework.util.CollectionUtils;

/**
 * Filter utility class. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-2
 */
public class FilterUtil {

    private static FilterUtil instance = new FilterUtil();

    private Map<String, List<SimpleWhereStatement>> filterMap = new ConcurrentHashMap<>();

    private FilterUtil() {
    }

    /**
     * Get the single instance.<br>
     * 
     * @return the single instance
     * @since SDNO 0.5
     */
    public static FilterUtil getInstance() {
        return instance;
    }

    /**
     * Get the needed filter by input original filter and attributes which require filtering.<br>
     * 
     * @param rawFilter original filter
     * @param attrs attributes which require filtering
     * @return filter
     * @since SDNO 0.5
     */
    public String getFilter(final String rawFilter, final Collection<String> attrs) {
        List<SimpleWhereStatement> simpleWhereStatements = filterMap.get(rawFilter);
        if(null == simpleWhereStatements) {
            simpleWhereStatements = parseFilter(rawFilter);
            filterMap.put(rawFilter, simpleWhereStatements);
        }

        return getFilter(simpleWhereStatements, attrs);
    }

    private List<SimpleWhereStatement> parseFilter(final String filter) {
        final String[] segmentStatements = filter.split(" and ");

        final List<SimpleWhereStatement> statements = new ArrayList<>(segmentStatements.length);

        for(final String segmentStatement : segmentStatements) {
            final SimpleWhereStatement statement = SimpleWhereStatement.fromRawStatement(segmentStatement);
            if(statement != null) {
                statements.add(statement);
            }
        }
        return statements;
    }

    private String getFilter(final List<SimpleWhereStatement> statements, final Collection<String> attrs) {
        final List<SimpleWhereStatement> selectedStatements = new ArrayList<>(attrs.size());

        for(final String attr : attrs) {
            for(final SimpleWhereStatement statement : statements) {
                if(statement.fit(attr)) {
                    selectedStatements.add(statement);
                    break;
                }
            }
        }

        if(CollectionUtils.isEmpty(selectedStatements)) {
            return null;
        }
        return join(selectedStatements);
    }

    private String join(final List<SimpleWhereStatement> statements) {
        final StringBuilder stringBuilder = new StringBuilder();
        for(Iterator<SimpleWhereStatement> iterator = statements.iterator(); iterator.hasNext();) {
            final SimpleWhereStatement next = iterator.next();
            stringBuilder.append(next.toStatement());
            if(iterator.hasNext()) {
                stringBuilder.append(" and ");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Simple WHERE statement class.<br>
     * <p>
     * Allows multiple conditions, conditions are connected with AND. Example: commonName like
     * '%:commonName%' will be analyze to: <br>
     * attrName : comonName <br>
     * rawStatement: commonName like '%:commonName%' <br>
     * </p>
     * 
     * @author
     * @version SDNO 0.5 2016-6-2
     */
    static class SimpleWhereStatement {

        private final String attrName;

        private final String rawStatement;

        /**
         * Constructor.<br>
         * 
         * @since SDNO 0.5
         * @param attrName attribute name
         * @param rawStatement raw statement
         */
        public SimpleWhereStatement(final String attrName, final String rawStatement) {
            this.attrName = attrName;
            this.rawStatement = rawStatement;
        }

        /**
         * Convert raw statement to SimpleWhereStatement object.<br>
         * 
         * @param rawStatement raw statement
         * @return SimpleWhereStatement object
         * @since SDNO 0.5
         */
        public static SimpleWhereStatement fromRawStatement(final String rawStatement) {
            final String[] split = rawStatement.split("(=[' ]*?:)|(like['% ]*?:)|(in[( ]*?:)", 2);
            if(split.length < 2) {
                return null;
            }
            return new SimpleWhereStatement(split[0].trim(), rawStatement);
        }

        /**
         * Get rawStatement.<br>
         * 
         * @return rawStatement
         * @since SDNO 0.5
         */
        public String toStatement() {
            return rawStatement;
        }

        @Override
        public String toString() {
            return rawStatement;
        }

        /**
         * Check input is equal to attrName or not.<br>
         * 
         * @param attr attribute name
         * @return boolean
         * @since SDNO 0.5
         */
        public boolean fit(final String attr) {
            return Objects.equals(attrName, attr);
        }
    }
}
