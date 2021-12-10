package com.agent.util;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class ImprovedNamingStrategy implements PhysicalNamingStrategy {
	
	@Override
	public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		// TODO Auto-generated method stub
		return convert(name);
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		// TODO Auto-generated method stub
		return convert(name);
	}
	@Override
	public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		// TODO Auto-generated method stub
		return convert(name);
	}
	
	
	public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return convert(name);
	};
	
	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		// TODO Auto-generated method stub
		return convert(name);
	}
	
	public ImprovedNamingStrategy() {
		// TODO Auto-generated constructor stub
	}
	
	private Identifier convert(Identifier identifier) {
        if (identifier == null || identifier.getText().equals("")) {
            return identifier;
        }

        String regex = "([a-z])([A-Z])";
        String replacement = "$1_$2";
        String newName = identifier.getText().replaceAll(regex, replacement).toLowerCase();
        return Identifier.toIdentifier(newName);
    }
}
