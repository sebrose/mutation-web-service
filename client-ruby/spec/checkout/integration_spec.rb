require 'spec_helper'

describe Checkout, 'integration' do
  let(:client) { Checkout::Client.build('http://localhost:9988', "test-team-#{Time.now.to_i}") }

  def strip(source)
    source = source.rstrip
    indent = source.scan(/^\s*/).min_by(&:length)
    source.gsub(/^#{indent}/, '') << "\n"
  end

  specify do
    expect(client.register).to be(client)

    expect(client.price_list).to eql(
      {
        'priceList' => {
          'entries'=> {
            'banana'=>{
              'itemCode' => 'banana',
              'unitPrice' => {
                'dollars' =>0,
                'cents'   =>25
              }
            }
          }
        }
      }
    )

    expect(client.current_requirements).to eql(
      'requirements' => strip(<<-TEXT
         Round 0

         GET the URL Checkout/Batch/your_team_name to retrieve a batch of baskets that you need to calculate the price of.

         Each basket has a unique ID
         In this round the batch will only contain a single basket
         In this round the basket will only contain a single item
         The item will be a banana and each banana costs 25c

         You PUT the result to /Checkout/Batch/your_team_name

         The JSON payload should look something like this:

             {"batch":{"baskets":{"1":{"dollars":0,"cents":75}}}}
              basket ID from batch ^
                        total cost of basket     ^          ^
                        (which you have calculated)
         TEXT
      )
    )
  end
end
