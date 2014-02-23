using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using CheckoutServiceProxy;
using CheckoutBusinessLogic;
using FakeItEasy;
using NinjaTurtles;
using NinjaTurtles.TestRunners;

namespace CheckoutBusinessLogicTest
{
    [TestClass]
    public class RequirementsRetrievalTest
    {
       [TestMethod, MutationTest]
        public void Requirements_Mutation_Tests()
        {
            MutationTestBuilder<Requirements>
                .For("retrieve")
                .WriteReportTo("RequirementsMutationReport.xml")
                .UsingRunner<MSTestTestRunner>()
                .Run();
        }
        
        [TestMethod]
        public void ShouldRetrieveRequirements()
        {
            // Arrange
            String teamName = "AnyName";
            String requirementDetail = "Round 0\nWell done!";
            Requirements requirements = new Requirements();

            ICheckoutClient client = A.Fake<ICheckoutClient>(x => x.Strict());

            A.CallTo(() => client.get<RequirementResponse>(String.Format("/Checkout/Requirements/{0}", teamName)))
                .ReturnsLazily((String path) => Tuple.Create(System.Net.HttpStatusCode.Created, new RequirementResponse { requirements = requirementDetail }));  
          
            // Act
            bool retrieved = requirements.retrieve(client, teamName);

            // Assert
            Assert.IsTrue(retrieved);
            Assert.AreEqual(requirementDetail, requirements.Detail);
        }
    }
}

