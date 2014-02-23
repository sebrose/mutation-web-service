using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CheckoutBusinessLogic
{
    public interface IOperation
    {
        String Execute(String url, String teamName);
    }
}
