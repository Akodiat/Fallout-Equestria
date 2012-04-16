public static Funtion<int,int> getFunction() {
    int counter = 0;

    return (x) => {
		    counter++;
		    return x + counter;
                   
		  };
}

public static void Main(String[] args) {
   var function = getFunction();

	
   Console.WriteLine(function(2));
   Console.WriteLine(function(2));	

}