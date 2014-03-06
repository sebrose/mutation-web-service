module Checkout
  class Client
    # Error raised on unexpected errors
    class Error < RuntimeError
      include Concord.new(:response)

      # Return error message
      #
      # @return [String]
      #
      # @api private
      #
      def message
        message = []
        message << "Unexpected response with code: #{response.status}"
        if response.content_type == APPLICATION_JSON
          message << MultiJSON.parse(response.body).inspect
        else
          message << response.body
        end
        message.join
      end

    end # RESTError
  end # Client
end # Checkout
