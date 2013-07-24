package Objects;


public enum UpdateProcessType 
{
	PLANLANDI(1),
	MONTAJ_TAMAMLANDI(2),
	CALISILIYOR(3),
	BEKLEMEDE(4),
	IPTAL(5),
	�S_EMR�_TAMAMLANDI(6);
	
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
	 if(Name.equalsIgnoreCase("Planland�")) 
		 return UpdateProcessType.PLANLANDI.getValue();
	 else if (Name.equalsIgnoreCase("�al���l�yor"))
		 return UpdateProcessType.CALISILIYOR.getValue();
     else if (Name.equalsIgnoreCase("Beklemede"))
    	 return UpdateProcessType.BEKLEMEDE.getValue();
	 else if (Name.equalsIgnoreCase("Montaj Tamamland�"))
		 return UpdateProcessType.MONTAJ_TAMAMLANDI.getValue();
	 else if (Name.equalsIgnoreCase("�ptal"))
		 return UpdateProcessType.IPTAL.getValue();
	 else if (Name.equalsIgnoreCase("�� Emri Tamamland�"))
		 return UpdateProcessType.�S_EMR�_TAMAMLANDI.getValue();
	 else 
		 return 1;
	 
	}

}
