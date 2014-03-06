module Checkout
  # REST client for checkout service
  class Client
    include Concord.new(:connection, :team_name)

    # Build client
    #
    # @param [String] uri
    # @param [String] team_name
    #
    # @return [Client]
    #
    # @api private
    #
    def self.build(uri, team_name)
      new(Faraday.new(uri), team_name)
    end

    # Build client from config file
    #
    # @param [String] filename
    #
    # @return [Client]
    #
    def self.from_config_file(filename)
      config = YAML.load_file(filename)
      server_uri       = config.fetch('server_uri')
      team_name = config.fetch('team_name')
      build(server_uri, team_name)
    end

    # Register team name
    #
    # @return [self]
    #   if successful
    #
    # @raise [Error]
    #   otherwise
    #
    # @api private
    #
    def register
      request = Request.put('/Checkout/Team', { name: team_name })
      run(request, 201)
      self
    end

    # Return current requirements
    #
    # @return [Hash]
    #   a hash containing requirements as plain text
    #
    # @raise [Error]
    #   in case there is any problem returing requirements
    #
    # @api private
    #
    def current_requirements
      request = Request.get("/Checkout/Requirements/#{team_name}")
      run(request)
    end

    # Return basket batches for pricing
    #
    # @return [Hash]
    #   a batch of baskets for pricing
    #
    # @raise [Error]
    #   in case there is any problem returning baskets
    #
    # @api private
    #
    def basket_batch
      request = Request.get("/Checkout/Batch/#{team_name}")
      run(request)
    end

    # Return price list
    #
    # @return [Hash]
    #   the current price list
    #
    # @raise [Error]
    #   in case there is any problem returing the price list
    #
    # @api private
    #
    def price_list
      request = Request.get("/Checkout/PriceList/#{team_name}")
      run(request)
    end

    # Return team score
    #
    # @return [Hash]
    #   the current team score
    #
    # @raise [Error]
    #   in case there is any problem returing the team score
    #
    # @api private
    #
    def current_score
      request = Request.get("/Checkout/Score/#{team_name}")
      run(request)
    end

    # Submit batch of calculations
    #
    # @param [Hash] batch
    #   the json payload
    #
    # @return [self]
    #   in case batch could be submitted successfuly
    #
    # @raise [Error]
    #   in case there is a problem with the submission
    #
    # @api private
    #
    def submit_batch(batch)
      request = Request.put("/Checkout/Batch/#{team_name}", batch)
      run(request)
    end

  private

    # Run reqeuest with status code expectation
    #
    # @return [H
    #   in case expected status code was returned
    #
    # @raise [Error]
    #   otherwise
    #
    # @api private
    #
    def run(request, expected_status_code = 200)
      response = request.run(connection)
      unless response.status == expected_status_code
        raise Error::StatusCode.new(response)
      end
      unless response.headers[:content_type] == JSON_CONTENT_TYPE
        raise Error::ContentType.new(response)
      end

      MultiJson.load(response.body)
    end

  end # Client
end # Checkout
