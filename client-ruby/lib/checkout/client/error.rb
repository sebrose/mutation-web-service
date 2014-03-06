module Checkout
  class Client
    # Error raised on unexpected errors
    class Error < RuntimeError
      include Concord.new(:response)

      # Error raised because of unexpected content type
      class ContentType < self

        # Return error message
        #
        # @return [String]
        #
        # @api private
        #
        def message
          "Unexpected response with content type: #{response.headers[:content_type]}"
        end

      end # StatusCode

      # Error raised because of unexpected status code
      class StatusCode < self

        # Return error message
        #
        # @return [String]
        #
        # @api private
        #
        def message
          message = []
          message << "Unexpected response with code: #{response.status}"
          if response.headers[:content_type] == JSON_CONTENT_TYPE
            message << MultiJson.load(response.body).inspect
          else
            message << response.body
          end
          message.join("\n")
        end

      end # StatusCode

    end # RESTError
  end # Client
end # Checkout
