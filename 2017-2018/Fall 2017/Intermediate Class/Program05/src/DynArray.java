public class DynArray 
{
	private double[] array;
	private int size;
	private int nextIndex;

	public DynArray()
	{
		this.size = 1;
		this.array = new double [this.size];
		this.nextIndex = 0;
	}

	public int arraySize() 
	{
		return this.size;

	}

	public int elements() 
	{
		return this.nextIndex;
	}

	public double at(int index)
	{
		if (0 <= index && index < nextIndex)
			return array[index];
		else
			return Double.NaN;
	}

	private void grow()
	{
		double [] newArray = new double [this.size *2];
		for(int i = 0; i< this.nextIndex; i++)
		{
			newArray[i] = array[i];
		}
		this.array = newArray;
		this.size *= 2;
	}

	private void shrink()
	{
		double [] newArray = new double [this.size /2];
		for(int i = 0; i< this.nextIndex; i++){
			newArray[i] = array[i];
		}
		this.array = newArray;
		this.size /= 2;
	}

	public void insertAt(int index, double value)
	{
		if(this.nextIndex == this.size)
		{
			this.grow();
		}
		if (0 <= index && index < this.nextIndex)
		{
			for (int i = this.nextIndex; i>index; --i)
			{
				this.array[i] = this.array[i-1];
			}
		}
		this.array[index] = value;
		++this.nextIndex;
	}

	public void insert(double value)
	{
		if (this.size == nextIndex){
			this.grow();
		}
		this.array[nextIndex++] = nextIndex;		

	}

	public double removeAt(int index)
	{
		if (0<= index && index < this.nextIndex)
		{
			double value = this.array[index];
			for (int i = index; i < this.nextIndex - 1; ++i)
			{
				this.array[i] = this.array[i + 1];
			}
			--this.nextIndex;
			if (this.nextIndex < (this.size/2))
			{
				this.shrink();
			}
			return value;
		}
		return Double.NaN;	
	}

	public double remove()
	{
		return this.removeAt(this.nextIndex - 1);
	}

	public void printArray()
	{
		for(int i=0; i<this.nextIndex; i++)
			System.out.println("array.at(" + i + ")= " + this.at(i));

	}
}
