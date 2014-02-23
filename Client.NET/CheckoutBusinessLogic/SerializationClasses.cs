using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CheckoutBusinessLogic
{
    public class RegistrationRequest
    {
        public String name { get; set; }
    }

    public class RegistrationResponse
    {
        public int id { get; set; }
        public String acceptedName { get; set; }
        public String errorMessage { get; set; }
    }

    public class RequirementResponse
    {
        public String requirements { get; set; }
        public String errorMessage { get; set; }
    }
}
