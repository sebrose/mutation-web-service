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
    public class TeamRegistrationTest
    {
       [TestMethod, MutationTest]
        public void Team_Mutation_Tests()
        {
            MutationTestBuilder<Team>
                .For("register")
                .WriteReportTo("TeamMutationReport.xml")
                .UsingRunner<MSTestTestRunner>()
                .Run();
        }
        
        [TestMethod]
        public void ShouldRegisterTeam()
        {
            // Arrange
            String teamName = "AnyName";
            Team team = new Team();

            ICheckoutClient client = A.Fake<ICheckoutClient>(x => x.Strict());

            A.CallTo(() => client.put<RegistrationResponse>("/Checkout/Team", A<RegistrationRequest>.Ignored))
                .ReturnsLazily((String path, Object req) => Tuple.Create(System.Net.HttpStatusCode.Created, new RegistrationResponse { acceptedName = ((RegistrationRequest)req).name }));  
          
            // Act
            bool registered = team.register(client, teamName);

            // Assert
            Assert.IsTrue(registered);
            Assert.AreEqual(teamName, team.Name);
        }
    }
}
