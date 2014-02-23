using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CheckoutBusinessLogic
{
    public class RegistrationOperation : IOperation
    {
        public String Execute(String url, String teamName)
        {
            CheckoutServiceProxy.ICheckoutClient client = CheckoutServiceProxy.CheckoutClientFactory.create(url);
            Team team = new Team();

            if (team.register(client, teamName))
            {
                return String.Format("Successfully registered:'{0}'", team.Name);
            }
            else
            {
                return team.ErrorMessage;
            }
        }
    }
}
