public class Queue extends DynArray{

	public int size()
	{
		return super.elements();
	}
	public boolean isEmpty()
	{
		if (super.elements() == 0)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	public void que(double value)
	{
		super.insert(value);
	}
	public double deQue()
	{
		if(this.isEmpty() != true)
		{
			return super.removeAt(0);
		}
			return Double.NaN;
	}
	public void queueDump()
	{
		for(int i = 0; i < super.elements(); i++)
		{
			System.out.println("array.at(" + i + ") = " + super.at(i));
		}
	}
}
