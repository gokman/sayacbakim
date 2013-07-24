package Objects;

public class EnumWorkOrderStatus {

	private static String Status;

	public static boolean setVisualEnable(String component, String Status) {
		if (Status.equalsIgnoreCase("Planland�")) 
		{
			
			if (component.equalsIgnoreCase("txtModemNumber"))
				return true;
			if (component.equalsIgnoreCase("txtSerialNumber"))
				return true;
			if (component.equalsIgnoreCase("txtOldSerialNumber"))
				return true;
			if (component.equalsIgnoreCase("txtTrafoKodu"))
				return true;
			if (component.equalsIgnoreCase("btnSerialBarcode"))
				return true;
			if (component.equalsIgnoreCase("btnModemBarcode"))
				return true;
			if (component.equalsIgnoreCase("btnOldSerialNumber"))
				return true;
			if (component.equalsIgnoreCase("description"))
				return true;
			if (component.equalsIgnoreCase("btnSave"))
				return true;
			if (component.equalsIgnoreCase("firstImage"))
				return true;
			if (component.equalsIgnoreCase("secondImage"))
				return false;
			
		} else if (Status.equalsIgnoreCase("�al���l�yor")) 
		{
			if (component.equalsIgnoreCase("txtModemNumber"))
				return true;
			if (component.equalsIgnoreCase("txtSerialNumber"))
				return true;
			if (component.equalsIgnoreCase("txtOldSerialNumber"))
				return true;
			if (component.equalsIgnoreCase("txtTrafoKodu"))
				return true;
			if (component.equalsIgnoreCase("btnSerialBarcode"))
				return true;
			if (component.equalsIgnoreCase("btnModemBarcode"))
				return true;
			if (component.equalsIgnoreCase("btnOldSerialNumber"))
				return true;
			if (component.equalsIgnoreCase("description"))
				return true;
			if (component.equalsIgnoreCase("btnSave"))
				return true;
			if (component.equalsIgnoreCase("firstImage"))
				return true;
			if (component.equalsIgnoreCase("secondImage"))
				return true;
		}

		else if (Status.equalsIgnoreCase("Beklemede")) 
		{
			if (component.equalsIgnoreCase("txtModemNumber"))
				return false;
			if (component.equalsIgnoreCase("txtSerialNumber"))
				return false;
			if (component.equalsIgnoreCase("txtOldSerialNumber"))
				return false;
			if (component.equalsIgnoreCase("txtTrafoKodu"))
				return false;
			if (component.equalsIgnoreCase("btnSerialBarcode"))
				return false;
			if (component.equalsIgnoreCase("btnModemBarcode"))
				return false;
			if (component.equalsIgnoreCase("btnOldSerialNumber"))
				return false;
			if (component.equalsIgnoreCase("description"))
				return true;
			if (component.equalsIgnoreCase("btnSave"))
				return true;
			if (component.equalsIgnoreCase("firstImage"))
				return false;
			if (component.equalsIgnoreCase("secondImage"))
				return false;
		}
		else if (Status.equalsIgnoreCase("Montaj Tamamland�")) 
		{

			if (component.equalsIgnoreCase("txtModemNumber"))
				return true;
			if (component.equalsIgnoreCase("txtSerialNumber"))
				return true;
			if (component.equalsIgnoreCase("txtOldSerialNumber"))
				return true;
			if (component.equalsIgnoreCase("txtTrafoKodu"))
				return true;
			if (component.equalsIgnoreCase("btnSerialBarcode"))
				return true;
			if (component.equalsIgnoreCase("btnModemBarcode"))
				return true;
			if (component.equalsIgnoreCase("btnOldSerialNumber"))
				return true;
			if (component.equalsIgnoreCase("description"))
				return true;
			if (component.equalsIgnoreCase("btnSave"))
				return true;
			if (component.equalsIgnoreCase("firstImage"))
				return true;
			if (component.equalsIgnoreCase("secondImage"))
				return true;

		}

		else if (Status.equalsIgnoreCase("�� Emri Tamamland�")) 
		{
			if (component.equalsIgnoreCase("txtModemNumber"))
				return false;
			if (component.equalsIgnoreCase("txtSerialNumber"))
				return false;
			if (component.equalsIgnoreCase("txtOldSerialNumber"))
				return false;
			if (component.equalsIgnoreCase("txtTrafoKodu"))
				return false;
			if (component.equalsIgnoreCase("btnSerialBarcode"))
				return false;
			if (component.equalsIgnoreCase("btnModemBarcode"))
				return false;
			if (component.equalsIgnoreCase("btnOldSerialNumber"))
				return false;
			if (component.equalsIgnoreCase("description"))
				return true;
			if (component.equalsIgnoreCase("btnSave"))
				return true;
			if (component.equalsIgnoreCase("firstImage"))
				return false;
			if (component.equalsIgnoreCase("secondImage"))
				return false;
		}

		else if (Status.equalsIgnoreCase("�ptal")) 
		{
			if (component.equalsIgnoreCase("txtModemNumber"))
				return false;
			if (component.equalsIgnoreCase("txtSerialNumber"))
				return false;
			if (component.equalsIgnoreCase("txtOldSerialNumber"))
				return false;
			if (component.equalsIgnoreCase("txtTrafoKodu"))
				return false;
			if (component.equalsIgnoreCase("btnSerialBarcode"))
				return false;
			if (component.equalsIgnoreCase("btnModemBarcode"))
				return false;
			if (component.equalsIgnoreCase("btnOldSerialNumber"))
				return false;
			if (component.equalsIgnoreCase("description"))
				return true;
			if (component.equalsIgnoreCase("btnSave"))
				return true;
			if (component.equalsIgnoreCase("firstImage"))
				return false;
			if (component.equalsIgnoreCase("secondImage"))
				return false;
		}
		return false;
	}

	public static String getWorkOrderStatus(String StatusType) 
	{
		if (StatusType.equalsIgnoreCase("1"))
			Status = "Planland�";
		else if (StatusType.equalsIgnoreCase("2"))
			Status = "�al���l�yor";
		else if (StatusType.equalsIgnoreCase("3"))
			Status = "Beklemede";
		else if (StatusType.equalsIgnoreCase("4"))
			Status = "Montaj Tamamland�";
		else if (StatusType.equalsIgnoreCase("5"))
			Status = "�� Emri Tamamland�";
		else if (StatusType.equalsIgnoreCase("6"))
			Status = "�ptal";
		else
			Status = "Planland�";
		return Status;
	}

	public static int getWorkOrderStatuCodeForNo(String StatusCode) {

		if (StatusCode.equalsIgnoreCase("1"))
			return 1;
		else if (StatusCode.equalsIgnoreCase("2"))
			return 2;
		else if (StatusCode.equalsIgnoreCase("3"))
			return 3;
		else if (StatusCode.equalsIgnoreCase("4"))
			return 4;
		else if (StatusCode.equalsIgnoreCase("5"))
			return 5;
		else if (StatusCode.equalsIgnoreCase("6"))
			return 6;
		else
			return 3;

	}

	public static int getWorkOrderStatuCodeForName(String Status) {
		if (Status.equalsIgnoreCase("Planland�"))
			return 1;
		else if (Status.equalsIgnoreCase("�al���l�yor"))
			return 2;
		else if (Status.equalsIgnoreCase("Beklemede"))
			return 3;
		else if (Status.equalsIgnoreCase("Montaj Tamamland�"))
			return 4;
		else if (Status.equalsIgnoreCase("�� Emri Tamamland�"))
			return 5;
		else if (Status.equalsIgnoreCase("�ptal"))
			return 6;
		else
			return 3;

	}

}
