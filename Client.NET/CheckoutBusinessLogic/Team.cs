using CheckoutServiceProxy;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CheckoutBusinessLogic
{
    public class Team
    {
        Tuple<System.Net.HttpStatusCode, RegistrationResponse> response;

        public String Name { get { return response.Item2.acceptedName; } }
        public String ErrorMessage { 
            get 
            {
                if (response.Item1 == ICheckoutClientConstants.SERVER_UNREACHABLE)
                {
                    return "Error contacting host";
                }
                else
                {
                    return String.Format("HttpStatusCode {0}\nError Message:{1}", response.Item1, response.Item2.errorMessage);
                } 
            } 
        }

        public bool register(ICheckoutClient client, String teamName)
        {
            response = client.put<RegistrationResponse>("/Checkout/Team", new RegistrationRequest { name = teamName });

            return (response.Item1 == System.Net.HttpStatusCode.Created);
        }
    }
}
