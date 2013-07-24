package Objects;


public enum UpdateProcessType 
{
	PLANLANDI(1),
	MONTAJ_TAMAMLANDI(2),
	CALISILIYOR(3),
	BEKLEMEDE(4),
	IPTAL(5),
	ÝS_EMRÝ_TAMAMLANDI(6);
	
	private int value;
	private String name;
	UpdateProcessType(int value) 
	{
		this.value = value;
	}
	public int getValue()
	{
		return value;
	}

	public int getName(String Name) 
	{
	 if(Name.equalsIgnoreCase("Planlandý")) 
		 return UpdateProcessType.PLANLANDI.getValue();
	 else if (Name.equalsIgnoreCase("Çalýþýlýyor"))
		 return UpdateProcessType.CALISILIYOR.getValue();
     else if (Name.equalsIgnoreCase("Beklemede"))
    	 return UpdateProcessType.BEKLEMEDE.getValue();
	 else if (Name.equalsIgnoreCase("Montaj Tamamlandý"))
		 return UpdateProcessType.MONTAJ_TAMAMLANDI.getValue();
	 else if (Name.equalsIgnoreCase("Ýptal"))
		 return UpdateProcessType.IPTAL.getValue();
	 else if (Name.equalsIgnoreCase("Ýþ Emri Tamamlandý"))
		 return UpdateProcessType.ÝS_EMRÝ_TAMAMLANDI.getValue();
	 else 
		 return 1;
	 
	}

}
