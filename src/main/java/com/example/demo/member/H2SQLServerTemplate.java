package com.example.demo.member;

import com.querydsl.sql.HSQLDBTemplates;

public class H2SQLServerTemplate extends HSQLDBTemplates {
    @Override
    protected boolean requiresQuotes(String identifier, boolean precededByDot) {

        if(isQuoted(identifier)) {
            return false;
        }

        return super.requiresQuotes(identifier, precededByDot);
    }

    public boolean isQuoted(String name) {
        return ( name.startsWith( "`" ) && name.endsWith( "`" ) )
                || ( name.startsWith( "[" ) && name.endsWith( "]" ) )
                || ( name.startsWith( "\"" ) && name.endsWith( "\"" ) );
             //   || (name.startsWith("\"")&& name.endsWith("\""));
    }
}
