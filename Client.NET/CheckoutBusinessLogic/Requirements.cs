using CheckoutServiceProxy;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CheckoutBusinessLogic
{
    public class Requirements
    {
        Tuple<System.Net.HttpStatusCode, RequirementResponse> response;

        public String Detail { get { return response.Item2.requirements; } }
        public String ErrorMessage
        {
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

        public bool retrieve(ICheckoutClient client, String teamName)
        {
            response = client.get<RequirementResponse>(String.Format("/Checkout/Requirements/{0}", teamName));

            return (response.Item1 == System.Net.HttpStatusCode.OK);
        }
    }
}
