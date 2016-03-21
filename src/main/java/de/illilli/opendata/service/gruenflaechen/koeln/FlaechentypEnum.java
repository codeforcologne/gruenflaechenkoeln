package de.illilli.opendata.service.gruenflaechen.koeln;

public enum FlaechentypEnum {

	ALL(0, "ALL", "Objekttyp > -1"), //
	KLEINGAERTEN(2, "Kleingärten", "Objekttyp = 2"), //
	SPIELPLATZ(4, "Spielplatz", "Objekttyp = 4"), //
	GRUENANLAGE(7, "Grünanlage", "Objekttyp = 7"), //
	FRIEDHOF(8, "Friedhof", "Objekttyp = 8"), //
	BIOTOP(9, "Biotopflächen", "Objekttyp = 9"), //
	SONDERFLAECHE(11, "Sonderflächen", "Objekttyp = 11"), //
	FORSTEIGENEFLAECHE(12, "Forsteigene Flächen", "Objekttyp = 12");

	private int id;
	private String typ;
	private String cqlPredicate;

	private FlaechentypEnum(int id, String typ, String cqlPredicate) {
		this.id = id;
		this.typ = typ;
		this.cqlPredicate = cqlPredicate;
	}

	public int id() {
		return id;
	}

	public String typ() {
		return typ;
	}

	public String cqlPredicate() {
		return cqlPredicate;
	}

	public static FlaechentypEnum getById(int id) {
		switch (id) {
		case 2:
			return KLEINGAERTEN;
		case 4:
			return SPIELPLATZ;
		case 7:
			return GRUENANLAGE;
		case 8:
			return FRIEDHOF;
		case 9:
			return BIOTOP;
		case 11:
			return SONDERFLAECHE;
		case 12:
			return FORSTEIGENEFLAECHE;
		default:
			return FlaechentypEnum.ALL;
		}
	}

}
