using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CheckoutServiceProxy
{
    public abstract class ICheckoutClientConstants
    {
        public static readonly System.Net.HttpStatusCode SERVER_UNREACHABLE = 0;
    }

    public interface ICheckoutClient
    {
        Tuple<System.Net.HttpStatusCode, T> put<T>(String path, Object req) where T : new();
        Tuple<System.Net.HttpStatusCode, T> get<T>(String path) where T : new();
     }
}
