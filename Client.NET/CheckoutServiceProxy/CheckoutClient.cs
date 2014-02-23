using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace CheckoutServiceProxy
{
    class CheckoutClient : ICheckoutClient
    {
        private String baseUrl;

        public CheckoutClient(String baseUrl_)
        {
            baseUrl = baseUrl_;
        }

        public Tuple<System.Net.HttpStatusCode, T> put<T>(string path, object req) where T : new()
        {
            var client = new RestClient(baseUrl);

            var request = new RestRequest(path, Method.PUT);
            request.RequestFormat = DataFormat.Json;
            request.AddBody(req);

            var response = client.Execute<T>(request);

            return new Tuple<System.Net.HttpStatusCode, T>(response.StatusCode, response.Data);
        }

        public Tuple<System.Net.HttpStatusCode, T> get<T>(string path) where T : new()
        {
            var client = new RestClient(baseUrl);

            var request = new RestRequest(path, Method.GET);
            request.RequestFormat = DataFormat.Json;

            var response = client.Execute<T>(request);

            return new Tuple<System.Net.HttpStatusCode, T>(response.StatusCode, response.Data);
        }
    }
}
