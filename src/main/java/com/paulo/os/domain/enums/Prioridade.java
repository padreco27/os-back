package com.paulo.os.domain.enums;

public enum Prioridade {

	BAIXA(0, "BAIXO"), MEDIA(1, "MEDIO"), ALTA(2, "ALTO");

	private Integer cod;
	private String descricao;

	private Prioridade(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public Integer getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Prioridade toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (Prioridade x : Prioridade.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Prioridade invalida " + cod );
	}
}
