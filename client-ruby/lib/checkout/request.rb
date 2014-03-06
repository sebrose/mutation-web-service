module Checkout

  # REST Request repesentation
  class Request
    include Adamantium::Flat, Concord.new(:verb, :path, :body)

    # Run request on connection
    #
    # @param [Faraday::Connection]
    #
    # @return [Faraday::Response]
    #
    # @api private
    #
    def run(connection)
      connection.public_send(verb, path) do |request|
        request.headers[:content_type] = JSON_CONTENT_TYPE
        if body
          request.body = MultiJson.dump(body)
        end
      end
    end

    [:head, :get, :post, :put, :delete].each do |verb|
      define_singleton_method(verb) do |path, body = nil|
        new(verb, path, body)
      end
    end

  end # Request
end # Checkout
