using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CheckoutBusinessLogic
{
    public class RequirementsOperation : IOperation
    {
        public String Execute(String url, String teamName)
        {
            CheckoutServiceProxy.ICheckoutClient client = CheckoutServiceProxy.CheckoutClientFactory.create(url);
            Requirements requirements = new Requirements();

            if (requirements.retrieve(client, teamName))
            {
                return String.Format("Requirements for this round:#n'{0}'", requirements.Detail);
            }
            else
            {
                return requirements.ErrorMessage;
            }
        }
    }
}
