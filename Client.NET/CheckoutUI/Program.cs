using CheckoutBusinessLogic;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CheckoutUI
{
    class Program
    {
        private static Dictionary<string, IOperation> operations = new Dictionary<string, IOperation> { 
            { "register", new RegistrationOperation() }, 
            { "requirements", new RequirementsOperation() }, 
            { "batch", null }
        };

        static void Main(string[] args)
        {
            switch (args.Length)
            {
                case 3:
                    IOperation operation;
                    if (operations.TryGetValue(args[0], out operation))
                    {
                        Console.Out.WriteLine(operation.Execute(args[1], args[2])); 
                    } 
                    else
                    {
                        Usage();
                    }
                    break;
                default:
                    Usage();
                    break;
            }
        }

        private static void Usage()
        {
            Console.Out.WriteLine("Usage: CheckoutConsole <op> <url> <team>\n" +
                                  "  <op>  : 'register', 'requirements' or 'batch'\n" +
                                  "  <url> : The URL and Port of the server e.g. 'http://172.16.66.1:9988'\n" +
                                  "  <team>: The team's name\n");
        }
    }
}
