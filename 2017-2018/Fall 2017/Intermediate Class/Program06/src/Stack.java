
public class Stack extends DynArray{
	
	public int size()
	{
		return super.elements();
	}
	public boolean isEmpty()
	{
		if (super.elements() > 0)
		{
			return false;
		}
		else 
		{
			return true;
		}
	}
	public void push(double value)
	{
		super.insert(value);
	}
	public double pop()
	{	
		if(isEmpty() != true)
		{
			return super.remove();
		}
		else 
		{
			return Double.NaN;
		}
	}
	public void stackDump()
	{
		for(int i = this.size()-1; i >= 0; --i)
		{
			System.out.println(super.at(i));
		}
	}
}