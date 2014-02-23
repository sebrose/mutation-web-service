using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CheckoutServiceProxy
{
    public class CheckoutClientFactory
    {
        public static ICheckoutClient create(String baseUrl)
        {
            return new CheckoutClient(baseUrl);
        }
    }
}
