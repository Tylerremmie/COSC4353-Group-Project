package cosc4353;

public class PaymentProxy implements GetPayment {
	public int givePlayerCredit()
	{
		return 100;
	}
	
	public int giveOtherPlayerCredit()
	{
		return 10;
	}
}
